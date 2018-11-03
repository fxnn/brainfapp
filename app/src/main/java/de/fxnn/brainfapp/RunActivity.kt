package de.fxnn.brainfapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.common.base.Stopwatch
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import de.fxnn.brainfuck.ProgramExecutor
import de.fxnn.brainfuck.interpreter.BrainfuckInterpreter
import de.fxnn.brainfuck.program.StringProgram
import de.fxnn.brainfuck.tape.InfiniteCharacterTape
import de.fxnn.brainfuck.tape.TapeEofBehaviour
import kotlinx.android.synthetic.main.activity_run.*
import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.nio.charset.Charset

const val CODE_EXTRA_KEY = "de.fxnn.brainfapp.Code"
val CHARSET = Charset.defaultCharset()

class RunActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)

        val code = intent!!.extras!![CODE_EXTRA_KEY]!!.toString()
        var byteArrayOutputStream = ByteArrayOutputStream()
        val dataOutput = DataOutputStream(byteArrayOutputStream)

        runBrainfuckProgram(code, dataOutput)

        val string = byteArrayOutputStream.toString(CHARSET.name())
        Toast.makeText(applicationContext, "Program produced ${string.length} chars", Toast.LENGTH_SHORT).show()

        textView_output.text = string
    }

    private fun runBrainfuckProgram(code: String, dataOutput: DataOutput) {
        val stopwatch = Stopwatch.createStarted()

        Toast.makeText(applicationContext, "Program of ${code.length} chars", Toast.LENGTH_SHORT).show()

        ProgramExecutor(
            StringProgram(code),
            BrainfuckInterpreter(
                InfiniteCharacterTape(CHARSET, TapeEofBehaviour.READS_ZERO),
                ByteStreams.newDataInput(ByteArray(0)),
                dataOutput
            )
        ).run()

        Toast.makeText(applicationContext, "Program ran in $stopwatch", Toast.LENGTH_SHORT).show()
    }
}
